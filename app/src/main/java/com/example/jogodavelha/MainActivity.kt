package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private var gameActive = true
    private var board = arrayOfNulls<String>(9)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout: GridLayout = findViewById(R.id.gridLayout)
        val statusTextView: TextView = findViewById(R.id.statusTextView)
        val restartButton: Button = findViewById(R.id.restartButton)

        // Configura os botões de cada célula
        for (i in 0 until gridLayout.childCount) {
            val button: Button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                if (gameActive && button.text == "") {
                    button.text = currentPlayer
                    board[i] = currentPlayer
                    if (checkForWin()) {
                        statusTextView.text = "Vitória do Jogador $currentPlayer!"
                        gameActive = false
                    } else if (board.all { it != null }) {
                        statusTextView.text = "Deu velha!"
                        gameActive = false
                    } else {
                        currentPlayer = if (currentPlayer == "X") "O" else "X"
                        statusTextView.text = "Vez do jogador $currentPlayer"
                    }
                }
            }
        }

        // Botão para reiniciar o jogo
        restartButton.setOnClickListener {
            resetGame(statusTextView, gridLayout)
        }
    }

    // Verifica se há um vencedor
    private fun checkForWin(): Boolean {
        val winPositions = arrayOf(
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8),
            intArrayOf(2, 4, 6)
        )
        for (positions in winPositions) {
            if (board[positions[0]] != null &&
                board[positions[0]] == board[positions[1]] &&
                board[positions[1]] == board[positions[2]]
            ) {
                return true
            }
        }
        return false
    }

    // Reinicia o jogo
    private fun resetGame(statusTextView: TextView, gridLayout: GridLayout) {
        currentPlayer = "X"
        gameActive = true
        board.fill(null)
        statusTextView.text = "Player X's turn"

        for (i in 0 until gridLayout.childCount) {
            val button: Button = gridLayout.getChildAt(i) as Button
            button.text = ""
        }
    }
}
