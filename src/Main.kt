/**
 * ===============================================================
 * Kotlin GUI Starter
 * ===============================================================
 *
 * This is a starter project for a simple Kotlin GUI application.
 * The Java Swing library is used, plus the FlatLAF look-and-feel
 * for a reasonably modern look.
 *
 * The app is structured to provide a simple view / model setup
 * with the App class storing application data (the 'model'), and
 * the MainWindow class providing the 'view'.
 */

import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Data fields
    val cats = mutableListOf<String>()
    var currentCat = 0

    // Setup the app model
    init {
        cats.add("Gary")
        cats.add("Sally")
        cats.add("Harry")
        cats.add("Tina")
        cats.add("Phil")
    }

    // Application logic functions

    fun nextCat() {
        currentCat++
        if (currentCat > cats.size - 1)
            currentCat = cats.size - 1
    }

    fun prevCat() {
        currentCat--
        if (currentCat < 0)
            currentCat = 0
    }

    fun newCat(newCatName: String) {
        cats.add(newCatName)
        currentCat = cats.lastIndex
    }
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var catNumLabel: JLabel
    private lateinit var catNameLabel: JLabel
    private lateinit var nextButton: JButton
    private lateinit var prevButton: JButton
    private lateinit var nameText: JTextField
    private lateinit var addButton: JButton

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Cat Scroller 5000"
        contentPane.preferredSize = Dimension(600, 500)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 64)

        catNumLabel = JLabel("#00")
        catNumLabel.horizontalAlignment = SwingConstants.RIGHT
        catNumLabel.bounds = Rectangle(50, 50, 100, 100)
        catNumLabel.font = baseFont
        add(catNumLabel)

        catNameLabel = JLabel("CAT NAME")
        catNameLabel.horizontalAlignment = SwingConstants.LEFT
        catNameLabel.bounds = Rectangle(200, 50, 350, 100)
        catNameLabel.font = baseFont
        add(catNameLabel)

        nextButton = JButton("⯈")
        nextButton.bounds = Rectangle(325,200,225,100)
        nextButton.font = baseFont
        nextButton.addActionListener(this)     // Handle any clicks
        add(nextButton)

        prevButton = JButton("⯇")
        prevButton.bounds = Rectangle(50,200,225,100)
        prevButton.font = baseFont
        prevButton.addActionListener(this)     // Handle any clicks
        add(prevButton)

        nameText = JTextField()
        nameText.bounds = Rectangle(50,350,300,100)
        nameText.font = baseFont
        nameText.addActionListener(this)
        add(nameText)

        addButton = JButton("+")
        addButton.bounds = Rectangle(400,350,150,100)
        addButton.font = baseFont
        addButton.addActionListener(this)     // Handle any clicks
        add(addButton)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        val catNum = app.currentCat + 1
        val catName = app.cats[app.currentCat]

        catNumLabel.text = "#$catNum"
        catNameLabel.text = catName

        prevButton.isEnabled = (app.currentCat > 0)
        nextButton.isEnabled = (app.currentCat < app.cats.size - 1)

        nameText.text = ""
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            nextButton -> {
                // Update Model
                app.nextCat()
                // And the view
                updateView()
            }
            prevButton -> {
                app.prevCat()
                updateView()
            }

            addButton, nameText -> {
                if (nameText.text.isNotBlank()) {
                    val name = nameText.text
                    app.newCat(name)
                    updateView()
                }
            }
        }
    }

}

