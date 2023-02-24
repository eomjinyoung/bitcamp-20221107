package bitcamp.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bitcamp.myapp.service.BoardService;

public class BoardListController implements PageController {

  private BoardService boardService;

  public BoardListController(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    request.setAttribute("boards",
        boardService.list(request.getParameter("keyword")));
    return "/board/list.jsp";
  }
}








