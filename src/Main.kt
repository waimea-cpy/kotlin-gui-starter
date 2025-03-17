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
 * Cat class
 */
class Cat(val name: String, val colour: String) {
    var alive = true

    fun kill() {
        alive = false
    }
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Data fields
    val cats = mutableListOf<Cat>()
    var currentCat = 0

    // Setup the app model
    init {
        cats.add(Cat("Gary", "Ginger"))
        cats.add(Cat("Sally", "Black"))
        cats.add(Cat("Harry", "White"))
        cats.add(Cat("Tina", "Tabby"))
        cats.add(Cat("Phil", "Tabby"))
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

    fun newCat(newCatName: String, newCatColour: String) {
        cats.add(Cat(newCatName,newCatColour))
        currentCat = cats.lastIndex
    }

    fun killCat() {
        val cat = cats[currentCat]
        cat.kill()
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
    private lateinit var catColourLabel: JLabel
    private lateinit var catAliveLabel: JLabel
    private lateinit var nextButton: JButton
    private lateinit var prevButton: JButton
    private lateinit var killButton: JButton
    private lateinit var nameText: JTextField
    private lateinit var colourText: JTextField
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
        contentPane.preferredSize = Dimension(900, 500)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 48)

        catNumLabel = JLabel("00")
        catNumLabel.horizontalAlignment = SwingConstants.CENTER
        catNumLabel.bounds = Rectangle(50, 50, 50, 100)
        catNumLabel.font = baseFont
        add(catNumLabel)

        catNameLabel = JLabel("NAME")
        catNameLabel.horizontalAlignment = SwingConstants.LEFT
        catNameLabel.bounds = Rectangle(150, 50, 250, 100)
        catNameLabel.font = baseFont
        add(catNameLabel)

        catColourLabel = JLabel("COLOUR")
        catColourLabel.horizontalAlignment = SwingConstants.LEFT
        catColourLabel.bounds = Rectangle(450, 50, 250, 100)
        catColourLabel.font = baseFont
        add(catColourLabel)

        catAliveLabel = JLabel("Y")
        catAliveLabel.horizontalAlignment = SwingConstants.CENTER
        catAliveLabel.bounds = Rectangle(750, 50, 100, 100)
        catAliveLabel.font = baseFont
        add(catAliveLabel)

        prevButton = JButton("⯇")
        prevButton.bounds = Rectangle(50,200,225,100)
        prevButton.font = baseFont
        prevButton.addActionListener(this)     // Handle any clicks
        add(prevButton)

        nextButton = JButton("⯈")
        nextButton.bounds = Rectangle(325,200,225,100)
        nextButton.font = baseFont
        nextButton.addActionListener(this)     // Handle any clicks
        add(nextButton)

        killButton = JButton("Dead")
        killButton.bounds = Rectangle(625,200,225,100)
        killButton.font = baseFont
        killButton.addActionListener(this)     // Handle any clicks
        add(killButton)

        nameText = JTextField()
        nameText.bounds = Rectangle(50,350,300,100)
        nameText.font = baseFont
        nameText.addActionListener(this)
        add(nameText)

        colourText = JTextField()
        colourText.bounds = Rectangle(400,350,300,100)
        colourText.font = baseFont
        colourText.addActionListener(this)
        add(colourText)

        addButton = JButton("+")
        addButton.bounds = Rectangle(750,350,100,100)
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
        val cat = app.cats[app.currentCat]

        val aliveColour = Color.WHITE
        val deadColour = Color(255, 170, 170)

        catNumLabel.text = catNum.toString()
        catNameLabel.text = cat.name
        catColourLabel.text = cat.colour
        catAliveLabel.text = if (cat.alive) "☻" else "☠"

        catNumLabel.foreground = if (cat.alive) aliveColour else deadColour
        catNameLabel.foreground = if (cat.alive) aliveColour else deadColour
        catColourLabel.foreground = if (cat.alive) aliveColour else deadColour
        catAliveLabel.foreground = if (cat.alive) aliveColour else deadColour

        prevButton.isEnabled = (app.currentCat > 0)
        nextButton.isEnabled = (app.currentCat < app.cats.size - 1)

        killButton.isEnabled = cat.alive
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

            killButton -> {
                app.killCat()
                updateView()
            }

            addButton,
            nameText,
            colourText -> {
                if (nameText.text.isNotBlank() && colourText.text.isNotBlank()) {
                    val name = nameText.text
                    val colour = colourText.text
                    app.newCat(name, colour)

                    nameText.text = ""
                    colourText.text = ""
                    updateView()
                    nameText.requestFocus()
                }
            }
        }
    }

}

