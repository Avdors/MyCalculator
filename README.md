# Hi! This is a simple calculator!
[![telegram32](https://github.com/Avdors/StoreBDandRoom/assets/99538385/ffb92fd5-a7d9-48cb-bf3e-2289171d48aa)](https://t.me/Avdors)
[![linkedin32](https://github.com/Avdors/StoreBDandRoom/assets/99538385/730aafa0-6543-4b95-9362-e8524c8f35ec)](https://www.linkedin.com/in/dmitrii-v-856187268/)
	
 The application is written in Android studio development environment (Giraffe 2022.3.1 Patch 4) , in java language.
The application uses one activiti, UI layout is done via LinearLayout.
In the application I use viewBinding. To determine which button was pressed I use findViewById, I substitute the id of the pressed button. 
MainActivity class implements OnClickListener interface. 
In the void onClick method I get the id of the View and process accordingly to the value:
For clearing, I use clear() method which clears the TextView
For the typical operations of addition, division, subtraction, multiplication, degree, I run the method calculate (here the parameter - the symbol of the command).
For degree expansion, I use the calculate() method separately.
To get the calculation I use performCalculation().
There are variables in the class:
    private ActivityMainBinding binding;
    private TextView tvResult;
    private String currentNumber = ""; - to save the entered number
    private String previousNumber = ""; - to save the entered number.

    private String operation = ""; - to specify the operation symbol

    private Boolean result_on_display = false; - gives information that in TextView is displayed now the result, so that if new numbers will be entered, the past result was automatically erased.
    private Boolean example_on_display = false; - gives information that in TextView, entered a ready example, on which we get the result, if you enter a new symbol of the operation, example, enter "5+5", continue entering and press "+", the display will automatically see the new value of "10+" and can continue to enter, for example, "10+2". In this way we see only the last entered example on the display.
By default we see 0 on the display, when entering an example, the zero is pre-cleared.
In the calculation procedure performCalculation(), I use an attempt, in case of entering an invalid character, for example, division by /., we get an exception and output the error on the display. I process division by 0 separately.
For calculations I use the predefined class Math.

 <img width="136" alt="2024-02-20_09-41-49" src="https://github.com/Avdors/MyCalculator/assets/99538385/ff6b778f-20e2-4ea6-8a15-88ee401f0f89">

