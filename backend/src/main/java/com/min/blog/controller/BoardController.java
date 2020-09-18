package com.min.blog.controller;


import com.min.blog.dto.*;
import com.min.blog.model.*;
import com.min.blog.repository.BoardRepository;
import com.min.blog.repository.CommentRepository;
import com.min.blog.repository.MainCategoryRepository;
import com.min.blog.repository.SubCategoryRepository;
import com.min.blog.service.UserService;
import com.min.blog.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    MainCategoryRepository mainCategoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    UserService userService;
    @Autowired
    MailSender mailSender;

    final String adminEmail="o_ogog@naver.com";
    final String FRONT_URL="http://www.1000min.kr";

    @GetMapping("/maincategory")
    public Object getCategory(@RequestParam int key){
        BasicResponse response = new BasicResponse();
        final BoardType type;
        if(key==0){
            type=BoardType.PORTFOLIO;
        }else{
            type=BoardType.BLOG;
        }

        response.status=true;
        response.object= mainCategoryRepository.findAllByType(type).stream().filter(mainCategory -> mainCategory.getType().equals(type)).sorted(Comparator.comparingInt(MainCategory::getSequenceNo)).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/onemaincategory")
    public Object getoneCategory(@RequestParam int subCategoryID){
        BasicResponse response = new BasicResponse();
        long id = subCategoryID;
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if(!subCategory.isPresent()){
            response.status=false;
            response.data="서브카테고리 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        Optional<MainCategory> mainCategory = mainCategoryRepository.findBySubcategories(subCategory.get());
        if(!mainCategory.isPresent()){
            response.status=false;
            response.data="메인카테고리 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        FullCategory fullCategory = new FullCategory();
        fullCategory.setMainCategory(mainCategory.get());
        fullCategory.setSubCategory(subCategory.get());
        response.status=true;
        response.object= fullCategory;


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/maincategory")
    public Object addMainCategory(@RequestBody Map map){
        BasicResponse response = new BasicResponse();
        String name = (String)map.get("name");
        int key = (Integer)map.get("type");
        int order = (Integer)map.get("order");
        BoardType type = BoardType.BLOG;
        if(key==0){
            type=BoardType.PORTFOLIO;
        }
        MainCategory mainCategory = new MainCategory();
        mainCategory.setName(name);
        mainCategory.setSequenceNo(order);
        mainCategory.setType(type);
        mainCategoryRepository.save(mainCategory);

        response.status=true;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/subcategory")
    public Object getSubCategory(@RequestParam Long mainCategoryID){
        BasicResponse response = new BasicResponse();
        Optional<MainCategory> mainCategory = mainCategoryRepository.findById(mainCategoryID);
        if(!mainCategory.isPresent()){
            response.status=false;
            response.data="메인카테고리 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        response.status=true;
        response.object= subCategoryRepository.findAllByMainCategory(mainCategory.get()).stream().sorted(Comparator.comparingInt(SubCategory::getSequenceNo)).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/subcategory")
    public Object addSubCategory(@RequestBody Map map){
        BasicResponse response = new BasicResponse();
        String name = (String)map.get("name");
        int intid = (int)map.get("mainCategoryID");
        long mainCategoryID=intid;
        int order = (Integer)map.get("order");
        Optional<MainCategory> mainCategory = mainCategoryRepository.findById(mainCategoryID);
        if(!mainCategory.isPresent()){
            response.status=false;
            response.data="메인카테고리 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        SubCategory subCategory= new SubCategory();
        subCategory.setName(name);
        subCategory.setSequenceNo(order);
        mainCategory.get().addSubCategory(subCategory);
        mainCategoryRepository.save(mainCategory.get());
        //subCategoryRepository.save(subCategory);

        response.status=true;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/allboardlist")
    public Object getAllBoardList(@RequestParam int key,@RequestParam int pageIndex){
        BasicResponse response = new BasicResponse();

        //int key=(int) map.get("type");
        final BoardType type;
        if(key==0){
            type=BoardType.PORTFOLIO;
        }else{
            type=BoardType.BLOG;
        }

        //int pageIndex = (Integer)map.getOrDefault("pageIndex",0);

        PageRequest pageRequest = PageRequest.of(pageIndex,5);
        BoardListDTO boardListDTO = new BoardListDTO();
        boardListDTO.setPageIndex(pageIndex);
        boardListDTO.setTotalPage(boardRepository.findAllByType(type,pageRequest).stream().count());
        boardListDTO.setBoards(boardRepository.findAllByType(type,pageRequest).stream().sorted(Comparator.comparing(BaseTimeEntity::getCreatedDate).reversed()).collect(Collectors.toList()));

        response.status=true;
        response.object=boardListDTO;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/boardlist")
    public Object getBoardList(@RequestParam int pageIndex, @RequestParam int subCategoryID){
        BasicResponse response = new BasicResponse();

        long id=subCategoryID;

        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if(!subCategory.isPresent()){
            response.status=false;
            response.data="서브카테고리 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        PageRequest pageRequest = PageRequest.of(pageIndex,10);
        BoardListDTO boardListDTO = new BoardListDTO();
        boardListDTO.setPageIndex(pageIndex);
        boardListDTO.setTotalPage(boardRepository.findAllBySubCategory(subCategory.get(), pageRequest).getTotalElements());
        boardListDTO.setBoards(boardRepository.findAllBySubCategory(subCategory.get(), pageRequest).stream().sorted(Comparator.comparing(BaseTimeEntity::getCreatedDate).reversed()).collect(Collectors.toList()));

        response.status=true;
        response.object=boardListDTO;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/board")
    public Object getBoard(@RequestParam int boardID){
        BasicResponse response = new BasicResponse();

        long id=boardID;
        Optional<Board> board = boardRepository.findById(id);

        if(!board.isPresent()){
            response.status=false;
            response.data="게시글 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        response.status=true;
        response.data=""+board.get().getSubCategory().getId();
        response.object=board.get();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/board")
    public Object addBoard(@RequestBody WriteBoard writeBoard){
        BasicResponse response = new BasicResponse();
        Board board = new Board();
        BoardType type = BoardType.BLOG;
        if(writeBoard.getType()==0){
            type=BoardType.PORTFOLIO;
        }
        Optional<SubCategory> subCategory = subCategoryRepository.findById(writeBoard.getSubCategoryID());
        if(!subCategory.isPresent()){
            response.status=false;
            response.data="서브카테고리 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        board.setSubCategory(subCategory.get());
        board.setTitle(writeBoard.getTitle());
        board.setContent(writeBoard.getContent());
        board.setType(type);
        subCategory.get().addBoard(board);
        boardRepository.save(board);
        subCategoryRepository.save(subCategory.get());

        response.status=true;
        response.object=board;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/editboard")
    public Object editBoard(@RequestBody EditBoard editBoard){
        BasicResponse response = new BasicResponse();

        Optional<Board> board = boardRepository.findById(editBoard.getBoardID());
        if(!board.isPresent()){
            response.status=false;
            response.data="보드 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        Optional<SubCategory> subCategory = subCategoryRepository.findById(editBoard.getSubCategoryID());
        if(!subCategory.isPresent()){
            response.status=false;
            response.data="서브 카테고리 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        board.get().setSubCategory(subCategory.get());
        board.get().setTitle(editBoard.getTitle());
        board.get().setContent(editBoard.getContent());
        if(board.get().getSubCategory().getId()!=subCategory.get().getId()){
            board.get().getSubCategory().removeBoard(board.get());
            subCategory.get().addBoard(board.get());
        }
        boardRepository.save(board.get());
        subCategoryRepository.save(subCategory.get());

        response.status=true;
        response.object=board;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/comment")
    private Object addComment(@RequestBody CommentDTO commentDTO){
        BasicResponse response = new BasicResponse();
        Optional<User> user = userService.findById(commentDTO.getUserId());
       if(!user.isPresent()){
            response.status=false;
            response.data="유저 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        Optional<Board> board = boardRepository.findById(commentDTO.getBoardId());
        if(!board.isPresent()){
            response.status=false;
            response.data="게시글 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        Comment comment = new Comment();
        comment.setUser(user.get());
        comment.setBoard(board.get());
        comment.setContents(commentDTO.getComment());
        board.get().addComment(comment);
        boardRepository.save(board.get());
        if(user.get().getRole()!=Role.ADMIN){
            final String url;
            if(board.get().getType()==BoardType.PORTFOLIO){
                url=FRONT_URL+"/portfolio/"+board.get().getId();
            }else {
                url=FRONT_URL+"/board/"+board.get().getId();
            }
            mailSender.sendNotification(adminEmail,board.get().getTitle(),url,user.get().getNickname(),comment.getContents());
        }

        response.status=true;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/comment")
    private Object getComment(@RequestParam int boardId){
        BasicResponse response = new BasicResponse();
        long id =boardId;
        Optional<Board> board = boardRepository.findById(id);

        if(!board.isPresent()){
            response.status=false;
            response.data="게시글 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        List<CommentListDTO> list = new ArrayList<>();
        for(Comment comment : commentRepository.findAllByBoard(board.get()).stream().sorted(Comparator.comparing(BaseTimeEntity::getCreatedDate)).collect(Collectors.toList())){
            CommentListDTO commentListDTO = new CommentListDTO();
            commentListDTO.setComment(comment);
            commentListDTO.setUser(comment.getUser());
            list.add(commentListDTO);
        }
        response.status=true;
        response.object=list;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/editcomment")
    private Object editComment(@RequestBody CommentEditDTO commentEditDTO){
        BasicResponse response = new BasicResponse();
        Optional<Board> board = boardRepository.findById(commentEditDTO.getBoardId());
        if(!board.isPresent()){
            response.status=false;
            response.data="게시글 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        Optional<Comment> comment = commentRepository.findById(commentEditDTO.getCommentId());
        if(!comment.isPresent()){
            response.status=false;
            response.data="댓글 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        comment.get().setContents(commentEditDTO.getComment());
        commentRepository.save(comment.get());
        board.get().getComments().remove(comment.get());
        board.get().addComment(comment.get());
        boardRepository.save(board.get());

        if(comment.get().getUser().getRole()!=Role.ADMIN){
            final String url;
            if(comment.get().getBoard().getType()==BoardType.PORTFOLIO){
                url=FRONT_URL+"/portfolio/"+comment.get().getBoard().getId();
            }else {
                url=FRONT_URL+"/board/"+comment.get().getBoard().getId();
            }
            mailSender.sendNotification(adminEmail,comment.get().getBoard().getTitle(),url,comment.get().getUser().getNickname(),comment.get().getContents());
        }

        response.status=true;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/deletecomment")
    private Object deleteComment(@RequestParam int boardId,@RequestParam int commentId){
        BasicResponse response = new BasicResponse();
        long id =boardId;
        Optional<Board> board = boardRepository.findById(id);

        if(!board.isPresent()){
            response.status=false;
            response.data="게시글 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        long cid =commentId;
        Optional<Comment> comment = commentRepository.findById(cid);
        board.get().getComments().remove(comment.get());
        commentRepository.delete(comment.get());

        response.status=true;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
