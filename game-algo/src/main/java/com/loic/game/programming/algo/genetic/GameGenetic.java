package com.loic.game.programming.algo.genetic;

import java.util.Objects;
import java.util.Random;

import com.loic.game.programming.algo.observer.GameDisableObserver;
import com.loic.game.programming.algo.observer.GameObserver;
import com.loic.game.programming.api.GameBoard;
import com.loic.game.programming.api.GameMove;
import com.loic.game.programming.api.GameMoveGenerator;

public class GameGenetic<B extends GameBoard, M extends GameMove<B>> {
  private final GameMoveGenerator<B, M> moveGenerator;
  private GameObserver<B, M> observer = GameDisableObserver.INSTANCE;

  public GameGenetic(GameMoveGenerator<B, M> moveGenerator) {
    this.moveGenerator = moveGenerator;
  }

  public M bestMove(B rootBoard, int depth) {
    GeneticAlgo<GameGene<M>> geneticAlgo = new GeneticAlgo<>(new GameResolver<>(rootBoard, moveGenerator, depth),
      gene -> {
        B curBoard = rootBoard;
        int curDepth = 0;
        for (M move : gene.moves()) {
          if (move != null) {
            curBoard = move.apply(curBoard);
            curDepth++;
          } else {
            break;
          }
        }
        return curBoard.evaluate(curDepth)[0];
      });

    GameGene<M> gene = geneticAlgo.iterate(1000, 100);
    return gene.firstMove();
  }

  public void setGameObserver(GameObserver<B, M> observer) {
    this.observer = Objects.requireNonNull(observer);
  }


  private static final class GameResolver<B extends GameBoard, M extends GameMove<B>> implements CandidateResolver<GameGene<M>> {
    private final B rootBoard;
    private final GameMoveGenerator<B, M> moveGenerator;
    private final int depth;

    private GameResolver(B rootBoard, GameMoveGenerator<B, M> moveGenerator, int depth) {
      this.rootBoard = rootBoard;
      this.moveGenerator = moveGenerator;
      this.depth = depth;
    }

    @Override
    public GameGene<M> generateRandomly(Random random) {

      return null;
    }

    @Override
    public GameGene<M> merge(GameGene<M> gene1, GameGene<M> gene2, Random random) {
      return null;
    }

    @Override
    public GameGene<M> mutate(GameGene<M> mGameGene, Random random) {
      return null;
    }
  }
}
