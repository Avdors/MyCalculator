package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private TextView tvResult;
    private String currentNumber = "";
    private String previousNumber = "";
    private String operation = "";

    private Boolean result_on_display = false;
    private Boolean example_on_display = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tvResult = binding.tvResult;
        int[] buttonsIds = {
                R.id.but_clear, R.id.but_degree, R.id.but_period, R.id.but_division,
                R.id.but_seven, R.id.but_eight, R.id.but_nine, R.id.but_multiplication,
                R.id.but_six, R.id.but_five, R.id.but_four, R.id.but_plus,
                R.id.but_three, R.id.but_two, R.id.but_one, R.id.but_minus,
                R.id.but_zero, R.id.but_equally
        };
        for(int id : buttonsIds) {
            Button button = findViewById(id);
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_clear:
                clear();
                break;
            case R.id.but_degree:
                calculate("^");
                break;
            case R.id.but_period:
                if (!currentNumber.contains(".")) {
                    currentNumber += ".";
                }
                break;
            case R.id.but_division:
                calculate("/");
                break;
            case R.id.but_multiplication:
                calculate("*");
                break;
            case R.id.but_plus:
                calculate("+");
                break;
            case R.id.but_minus:
                calculate("-");
                break;
            case R.id.but_equally:
                performCalculation();
                break;
            default:
                //если введен пример на дисплее и мы без операции вводим новое число
                //то очищаем дисплей от прошлого примера и вносим новые данные
                if (result_on_display || currentNumber == "ERROR") {
                    clear();
            }
                if(currentNumber == "0" && operation.isEmpty() && previousNumber.isEmpty()) currentNumber = "";

                String number = ((Button) view).getText().toString();
                currentNumber += number;
                break;
        }


        //обновляем данные на дисплее только при нажатии только на "="
        //или при введении второй операции в строке, чтобы видеть какой пример сейчас внесен в строку
        updateDisplay();
    }

    private void calculate(String newOperation) {
        // если вводим новую операцию при наличи непосчитанного примера
        // сперва посчитаем результат, затем поставим к нему новую операцию
        if(currentNumber != "ERROR"){
        if (example_on_display) {
            performCalculation();
            example_on_display = false;
        }

        if(operation == "") {
            previousNumber = currentNumber;
            currentNumber = "";
            operation = newOperation;
            result_on_display = false;

        }
        // если на дисплее уже установлена операция и мы ввели новую, заменять текущую операцию на новую, не затирая данные строки
        else operation = newOperation;}
        else clear();
    }
    private void updateDisplay() {
        String displayText = previousNumber + operation + currentNumber;
        //проверяем имеется ли полный пример на дисплее, два значения и операция
        if(!operation.isEmpty() && !currentNumber.isEmpty()) example_on_display = true;
        tvResult.setText(displayText);
    }

    private void performCalculation() {
        if (!previousNumber.isEmpty() && !currentNumber.isEmpty()) {
            double result = 0;
            //проверяю можно ли привести к типу дабл значение элементов уравнения
            //если нет, выдаю ошибку
            try {
                double prevNum = Double.parseDouble(previousNumber);
                double currNum = Double.parseDouble(currentNumber);



            int maxDecimalPlaces = Math.max(decimalPlaces(previousNumber), decimalPlaces(currentNumber));

            switch (operation) {
                case "+":
                    // Использую roundResult для сложения и умножения, для верного округления
                    result = roundResult(prevNum + currNum, maxDecimalPlaces);
                    break;
                case "*":
                    // Использую roundResult для сложения и умножения, для верного округления
                    result = roundResult(prevNum * currNum, maxDecimalPlaces);
                    break;
                case "-":
                    result = prevNum - currNum;
                    break;
                case "/":
                    if(prevNum !=0 && currNum != 0) {
                        result = prevNum / currNum;
                    }
                    else result = 0;
                    break;
                case "^":
                    result = Math.pow(prevNum, currNum);
                    break;
            }


            if (result == (long) result) {
                // If the result is a whole number, convert it to an integer.
                currentNumber = String.valueOf((long) result);
            } else {
                // Else, keep it as a double.
                currentNumber = String.valueOf(result);
            }
            } catch (NumberFormatException e) {
                currentNumber = "ERROR";
            }

          //  currentNumber = String.valueOf(result);
            previousNumber = "";
            operation = "";
            result_on_display = true;
        }
    }

    private void clear() {
        currentNumber = "0";
        previousNumber = "";
        operation = "";
        result_on_display = false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private int decimalPlaces(String number) {
        if (!number.contains(".")) return 0;
        return number.length() - number.indexOf('.') - 1;
    }

    private double roundResult(double value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(value * scale) / scale;
    }
}