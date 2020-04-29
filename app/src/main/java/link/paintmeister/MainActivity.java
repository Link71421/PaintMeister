package link.paintmeister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Entry point into the application. This will display the splash screen before displaying the
 * main menu.
 *
 * @author Dillon Ramsey
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Pause time in milliseconds
     */
    private static final int PAUSE = 5000;
    /**
     * Creating the runner and implementing the run method
     */
    final Runnable runner = new Runnable() {
        /**
         * Implementation of the run method to call goToNextScreen
         */
        @Override
        public void run() {
            goToNextScreen();
        }
    };
    /**
     * Handler for the delay
     */
    private Handler handler;

    /**
     * onCreate for the splash screen. Sets the content view to the appropriate layout.
     *
     * @param savedInstanceState - Any bundled savedInstanceState data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This is where the delay for the splash screen will occur using the handler
     */
    protected void onStart() {
        super.onStart();

        handler = new Handler();
        handler.postDelayed(runner, PAUSE);
    }

    /**
     * Will move the app to the MainMenu and close the splash screen
     */
    private void goToNextScreen() {
        Intent menu = new Intent(this, link.paintmeister.PaintScreen.class);
        this.startActivity(menu);
        this.finish();
    }

}
