package bitcamp.myapp.service.impl;

import java.util.List;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;
import bitcamp.util.TransactionManager;

public class DefaultBoardService implements BoardService {

  private TransactionManager txManager;
  private BoardDao boardDao;
  private BoardFileDao boardFileDao;

  public DefaultBoardService(TransactionManager txManager, BoardDao boardDao, BoardFileDao boardFileDao) {
    this.txManager = txManager;
    this.boardDao = boardDao;
    this.boardFileDao = boardFileDao;
  }

  @Override
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

  @Override
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

  @Override
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


  @Override
  public BoardFile getFile(int fileNo) {
    return boardFileDao.findByNo(fileNo);
  }

  @Override
  public void deleteFile(int fileNo) {
    boardFileDao.delete(fileNo);
  }
}





