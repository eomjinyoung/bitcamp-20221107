package bitcamp.myapp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;

@Service
public class DefaultBoardService implements BoardService {

  @Autowired private BoardDao boardDao;
  @Autowired private BoardFileDao boardFileDao;

  @Transactional
  @Override
  public void add(Board board) {
    boardDao.insert(board);
    if (board.getAttachedFiles().size() > 0) {
      for (BoardFile boardFile : board.getAttachedFiles()) {
        boardFile.setBoardNo(board.getNo());
      }
      boardFileDao.insertList(board.getAttachedFiles());
    }
  }

  @Override
  public List<Board> list(String keyword) {
    return boardDao.findAll(keyword);
  }

  @Override
  public Board get(int no) {
    Board b = boardDao.findByNo(no);
    if (b != null) {
      boardDao.increaseViewCount(no);
    }
    return b;
  }

  @Transactional
  @Override
  public void update(Board board) {
    if (boardDao.update(board) == 0) {
      throw new RuntimeException("게시글이 존재하지 않습니다!");
    }
    if (board.getAttachedFiles().size() > 0) {
      boardFileDao.insertList(board.getAttachedFiles());
    }
  }

  @Transactional
  @Override
  public void delete(int no) {
    boardFileDao.deleteOfBoard(no);
    if (boardDao.delete(no) == 0) {
      throw new RuntimeException("게시글이 존재하지 않습니다!");
    }
  }


  @Override
  public BoardFile getFile(int fileNo) {
    return boardFileDao.findByNo(fileNo);
  }

  @Override
  public void deleteFile(int fileNo) {
    boardFileDao.delete(fileNo);
  }
}





