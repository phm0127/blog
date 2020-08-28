package com.min.blog.controller;


import com.min.blog.dto.BoardListDTO;
import com.min.blog.dto.WriteBoard;
import com.min.blog.model.*;
import com.min.blog.repository.BoardRepository;
import com.min.blog.repository.CommentRepository;
import com.min.blog.repository.MainCategoryRepository;
import com.min.blog.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
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


    @GetMapping("/maincategory")
    public Object getCategory(@RequestBody Map map){
        BasicResponse response = new BasicResponse();
        if(!map.containsKey("type")){
            response.status=false;
            response.data="카테고리 타입 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        int key = (Integer)map.get("type");
        BoardType type = BoardType.BLOG;
        if(key==0){
            type=BoardType.PORTFOLIO;
        }

        response.status=true;
        response.object= mainCategoryRepository.findAllByType(type).stream().sorted(Comparator.comparingInt(MainCategory::getSequenceNo)).collect(Collectors.toList());

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
    public Object getSubCategory(@RequestBody Map map){
        BasicResponse response = new BasicResponse();
        long mainCategoryID=(Long) map.get("mainCategoryID");
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

    @PutMapping("/subcategory")
    public Object addSubCategory(@RequestBody Map map){
        BasicResponse response = new BasicResponse();
        String name = (String)map.get("name");
        long mainCategoryID=(Long) map.get("mainCategoryID");
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
        subCategoryRepository.save(subCategory);

        response.status=true;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/allboardlist")
    public Object getAllBoardList(@RequestParam int key,@RequestParam int pageIndex){
        BasicResponse response = new BasicResponse();
        System.out.println(key+","+pageIndex);
        //int key=(int) map.get("type");
        BoardType type = BoardType.BLOG;
        if(key==0){
            type=BoardType.PORTFOLIO;
        }
        //int pageIndex = (Integer)map.getOrDefault("pageIndex",0);

        PageRequest pageRequest = PageRequest.of(pageIndex,10);
        BoardListDTO boardListDTO = new BoardListDTO();
        boardListDTO.setPageIndex(pageIndex);
        boardListDTO.setTotalPage(boardRepository.findAll(pageRequest).getTotalElements());
        boardListDTO.setBoards(boardRepository.findAll(pageRequest).stream().sorted(Comparator.comparing(BaseTimeEntity::getCreatedDate)).collect(Collectors.toList()));

        response.status=true;
        response.object=boardListDTO;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/boardlist")
    public Object getBoardList(@RequestBody Map map){
        BasicResponse response = new BasicResponse();

        long subCategoryID=(Long) map.get("subCategoryID");

        int pageIndex = (Integer)map.getOrDefault("pageIndex",0);
        Optional<SubCategory> subCategory = subCategoryRepository.findById(subCategoryID);
        if(!subCategory.isPresent()){
            response.status=false;
            response.data="서브카테고리 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        PageRequest pageRequest = PageRequest.of(pageIndex,10);
        BoardListDTO boardListDTO = new BoardListDTO();
        boardListDTO.setPageIndex(pageIndex);
        boardListDTO.setTotalPage(boardRepository.findAllBySubCategory(subCategory.get(), pageRequest).getTotalElements());
        boardListDTO.setBoards(boardRepository.findAllBySubCategory(subCategory.get(), pageRequest).stream().sorted(Comparator.comparing(BaseTimeEntity::getCreatedDate)).collect(Collectors.toList()));

        response.status=true;
        response.object=boardListDTO;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/board")
    public Object getBoard(@RequestBody Map map){
        BasicResponse response = new BasicResponse();
        long id=(Long) map.get("id");
        Optional<Board> board = boardRepository.findById(id);
        if(!board.isPresent()){
            response.status=false;
            response.data="게시글 정보 없음";
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        response.status=true;
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
