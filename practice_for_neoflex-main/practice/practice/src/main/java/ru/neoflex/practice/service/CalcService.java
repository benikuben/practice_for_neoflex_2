package ru.neoflex.practice.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;


@Service
public class CalcService {
    public Double sum(List<Double> numbers) {
        return numbers.stream().reduce((sum, number) -> sum + number).get();
    }

    public Double diff(List<Double> numbers) {
        return numbers.stream().reduce((diff, number) -> diff - number).get();
    }

    public Double product(List<Double> numbers) {
        return numbers.stream().reduce((product, number) -> product * number).get();
    }

    public Double quotient(List<Double> numbers) {
        return numbers.stream().reduce((quotient, number) -> quotient / number).get();
    }

    /**
     * Сначала выполняются операции деления и умножения, затем вычитания и сложения
     *
     * @param numbers    числа, используемые в выражении
     * @param operations арифметические операции, используемые в выражении
     * @return значение выражения
     */
    public Double multiOperation(List<Double> numbers, List<String> operations) {
        performOperation(numbers, operations, "divide", "multiply");
        performOperation(numbers, operations, "subtract", "add");

        return numbers.get(0);
    }

    /**
     * Посик (индексов) заданных арифметических операций
     * Вычисление результата операции (найденный индекс операции в operations совпадает с индексом первого компонента операции в numbers)
     * Удаление найденной операции из списка
     *
     * @param o1,o2 пара арифметических операций
     */
    private void performOperation(List<Double> numbers, List<String> operations, String o1, String o2) {
        ListIterator<String> operationsIter = operations.listIterator();
        while (operationsIter.hasNext()) {
            String operation = operationsIter.next();

            if (operation.equals(o1) || operation.equals(o2)) {
                int index = operations.indexOf(operation);
                setValueOfOperationResult(numbers, operation, index);
                operationsIter.remove();
            }
        }
    }

    /**
     * Вычисляется результат выполнения операции и записывается на место первого компонента математического дейсвия,
     * второй компонент удаляется
     *
     * @param operation математическое дейсвие
     * @param index     индекс первого компонента
     */
    private void setValueOfOperationResult(List<Double> numbers, String operation, int index) {
        Double numberOne = numbers.get(index);
        Double numberTwo = numbers.get(index + 1);
        switch (operation) {
            case "divide":
                numbers.set(index, numberOne / numberTwo);
                break;
            case "multiply":
                numbers.set(index, numberOne * numberTwo);
                break;
            case "subtract":
                numbers.set(index, numberOne - numberTwo);
                break;
            case "add":
                numbers.set(index, numberOne + numberTwo);
                break;
        }
        numbers.remove(index + 1);
    }
}
