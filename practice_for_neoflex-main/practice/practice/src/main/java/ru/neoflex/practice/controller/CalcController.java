package ru.neoflex.practice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.neoflex.practice.service.CalcService;
import ru.neoflex.practice.util.MathematicalExpressionException;

import java.util.*;

@RestController
public class CalcController {
    private final CalcService calcService;

    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    /**
     * Вычисляет сумму всех параметров запроса с именем number
     */
    @GetMapping("/add")
    public Double getAddition(@RequestParam("number") List<Double> numbers) {
        return calcService.sum(numbers);
    }

    /**
     * Вычисляет разность всех параметров запроса с именем number
     */
    @GetMapping("/subtract")
    public Double getSubtraction(@RequestParam("number") List<Double> numbers) {
        return calcService.diff(numbers);
    }

    /**
     * Вычисляет произведение всех параметров запроса с именем number
     */
    @GetMapping("/multiply")
    public Double getMultiplication(@RequestParam("number") List<Double> numbers) {
        return calcService.product(numbers);
    }

    /**
     * Вычисляет частное всех параметров запроса с именем number
     */
    @GetMapping("/divide")
    public Double getDivision(@RequestParam("number") List<Double> numbers) {
        return calcService.quotient(numbers);
    }

    /**
     * Вычисляет результат математического выражения, в котором параметры запроса с именем number - числа,
     * параметры запроса с именем operation - арифметические дейсвия
     * (divide - деление, multiply - умножение, add - сложение, subtract - вычитание)
     */
    @GetMapping("/multiOperation")
    public Double getMultiOperation(HttpServletRequest request,
                                    @RequestParam(value = "number", required = false) List<Double> numbers,
                                    @RequestParam(value = "operation", required = false) List<String> operations) {
        if (request.getQueryString() == null ||
                !request.getQueryString().matches("(number=\\d+(\\.\\d+)*&operation=(divide|multiply|add|subtract)&)+number=\\d+(\\.\\d+)*"))
            throw new MathematicalExpressionException("Некорректный синтаксис математического выражения. " +
                    "Выражение должно содерджать минмиум два числа и по крайней мере одну математическую операцию.");
        return calcService.multiOperation(numbers, operations);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(MathematicalExpressionException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>("Некорректно введены данные.", HttpStatus.BAD_REQUEST);
    }
}

