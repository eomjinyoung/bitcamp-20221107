package bitcamp.myapp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.BoardFileDao;
import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardFile;

@Service
public class DefaultBoardService implements BoardService {

  @Autowired private PlatformTransactionManager txManager;
  @Autowired private BoardDao boardDao;
  @Autowired private BoardFileDao boardFileDao;

  @Override
  public void add(Board board) {
    // 트랜잭션 동작을 설정
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 위에서 설정한 대로 동작할 트랜잭션을 준비
    TransactionStatus status = txManager.getTransaction(def);
    try {
      boardDao.insert(board);
      if (board.getAttachedFiles().size() > 0) {
        for (BoardFile boardFile : board.getAttachedFiles()) {
          boardFile.setBoardNo(board.getNo());
        }
        boardFileDao.insertList(board.getAttachedFiles());
      }
      txManager.commit(status); // 트랜잭션 정책에 따라 commit 수행

    } catch (Exception e) {
      txManager.rollback(status); // 틀랜잭션 정책에 따라 rollback 수행
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
    // 트랜잭션 동작을 설정
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 위에서 설정한 대로 동작할 트랜잭션을 준비
    TransactionStatus status = txManager.getTransaction(def);
    try {
      if (boardDao.update(board) == 0) {
        throw new RuntimeException("게시글이 존재하지 않습니다!");
      }
      if (board.getAttachedFiles().size() > 0) {
        boardFileDao.insertList(board.getAttachedFiles());
      }
      txManager.commit(status);

    }  catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }

  @Override
  public void delete(int no) {
    // 트랜잭션 동작을 설정
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

    // 위에서 설정한 대로 동작할 트랜잭션을 준비
    TransactionStatus status = txManager.getTransaction(def);
    try {
      boardFileDao.deleteOfBoard(no);
      if (boardDao.delete(no) == 0) {
        throw new RuntimeException("게시글이 존재하지 않습니다!");
      }
      txManager.commit(status);

    }  catch (Exception e) {
      txManager.rollback(status);
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





