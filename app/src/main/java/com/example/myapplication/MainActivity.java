package com.example.myapplication;
/*
* auhthor: Ubaid Ahmad
* date: 10/05/2020
*
*
 */
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    int active_player = 0;
    boolean winner = false;
    boolean draw = false;
    int[] block_status;

    {
        block_status = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    }

    int[][] winning_positions = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIt(View view) {
        ImageView choice = (ImageView) view;
        choice.setAlpha(0f);
        choice.animate().alpha(1f).setDuration(500).rotation(900);
        int current_block = Integer.parseInt(choice.getTag().toString());
        if( block_status[current_block] == 2 ){
            block_status[current_block] = active_player;
            if (active_player == 0) {
                choice.setImageResource(R.drawable.naught);
                active_player = 1;
            } else if( active_player == 1 ) {
                active_player = 0;
                choice.setImageResource(R.drawable.cross);
            }
            for( int[] winning_position : winning_positions ){
                if( block_status[winning_position[0]] == block_status[winning_position[1]] && block_status[winning_position[1]] == block_status[winning_position[2]] && block_status[winning_position[0]] != 2 ){
                    winner = true;
                }
            }
            boolean maybeDraw = true;
            for( int x : block_status ){
                System.out.print(x +"  ");
                if( x == 2 ){
                    maybeDraw = false;
                    System.out.print( maybeDraw + "  ");
                }
            }
            System.out.println();
            if( maybeDraw && !winner){
                System.out.println("DRAW VALUE CHANGED");
                draw = true;
            }
            if( winner && !draw ){

                ConstraintLayout notParent = (ConstraintLayout)(findViewById(R.id.notParent));
                notParent.setVisibility(View.VISIBLE);
                GridLayout board = (GridLayout)(findViewById(R.id.board));
                board.setAlpha(0.2f);
                TextView result = (TextView)(findViewById(R.id.result));
                int winning_player = 1;
                if( active_player == 0 ){
                    winning_player = 2;
                }
                String message = "Player " + winning_player + " won!!";
                result.setText(message);
            }else if( draw ){
                System.out.println(winner);
                ConstraintLayout notParent = (ConstraintLayout)(findViewById(R.id.notParent));
                notParent.setVisibility(View.VISIBLE);
                GridLayout board = (GridLayout)(findViewById(R.id.board));
                board.setAlpha(0.2f);
                TextView result = (TextView)(findViewById(R.id.result));
                String message = " Draw :( ";
                result.setText(message);
            }

        }
    }

    public void playAgain(View view) {

        ConstraintLayout notParent = (ConstraintLayout)(findViewById(R.id.notParent));
        notParent.setVisibility(View.INVISIBLE);
        GridLayout board = (GridLayout)(findViewById(R.id.board));
        board.setAlpha(1f);
        winner = false;
        active_player = 0;
        for (int i = 0; i < block_status.length; i++) {
            block_status[i] = 2;
            ((ImageView) board.getChildAt(i)).setImageResource(0);
        }
        draw = false;
    }
}