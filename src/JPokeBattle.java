import javax.swing.SwingUtilities;

import it.pokebattle.ui.MainScreen;

/**
 * Classe principale del gioco JPokeBattle, un clone del sistema di combattimento Pokémon.
 * Contiene il metodo main per avviare l'applicazione.
 */
public class JPokeBattle {
    public static void main(String[] args) {
        // Avvia l'interfaccia grafica nel thread di Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crea e mostra la schermata principale
                MainScreen mainScreen = new MainScreen();
                mainScreen.setVisible(true);
            }
        });
    }
}