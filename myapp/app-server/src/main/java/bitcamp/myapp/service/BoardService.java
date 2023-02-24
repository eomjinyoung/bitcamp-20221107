package bitcamp.myapp.service;

import java.util.List;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.util.TransactionManager;

public class BoardService {

  private TransactionManager txManager;
  private BoardDao boardDao;
  private BoardFileDao boardFileDao;

  public BoardService(TransactionManager txManager, BoardDao boardDao, BoardFileDao boardFileDao) {
    this.txManager = txManager;
    this.boardDao = boardDao;
    this.boardFileDao = boardFileDao;
  }

  public void add(Board board) {
    try {
      txManager.startTransaction();
      boardDao.insert(board);
      if (board.getAttachedFiles().size() > 0) {
        for (BoardFile boardFile : board.getAttachedFiles()) {
          boardFile.setBoardNo(board.getNo());
        }
        boardFileDao.insertList(board.getAttachedFiles());
      }
      txManager.commit();

    } catch (Exception e) {
      txManager.rollback();
      throw new RuntimeException(e);
    }
  }

  public List<Board> list(String keyword) {
    return boardDao.findAll(keyword);
  }

  public Board get(int no) {
    Board b = boardDao.findByNo(no);
    if (b != null) {
      boardDao.increaseViewCount(no);
    }
    return b;
  }

  public void update(Board board) {
    try {
      txManager.startTransaction();
      if (boardDao.update(board) == 0) {
        throw new RuntimeException("게시글이 존재하지 않습니다!");
      }
      if (board.getAttachedFiles().size() > 0) {
        boardFileDao.insertList(board.getAttachedFiles());
      }
      txManager.commit();

    }  catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }

  public void delete(int no) {
    try {
      txManager.startTransaction();
      boardFileDao.deleteOfBoard(no);
      if (boardDao.delete(no) == 0) {
        throw new RuntimeException("게시글이 존재하지 않습니다!");
      }
      txManager.commit();

    }  catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }

  public void deleteFile(int fileNo) {
    boardFileDao.delete(fileNo);
  }
}





