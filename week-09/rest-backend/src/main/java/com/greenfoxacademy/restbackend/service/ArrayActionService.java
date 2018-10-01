package com.greenfoxacademy.restbackend.service;

import com.greenfoxacademy.restbackend.model.dto.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArrayActionService {
    public Result doArrayAction(String action, List<Integer> numbers) {
        switch (action) {
            case "sum":
                return sum(numbers);
            case "multiply":
                return multiply(numbers);
            case "double":
                return applyDouble(numbers);
            default:
                throw new IllegalArgumentException("Unsupported action: " + action);
        }
    }

    private Result<Integer> sum(List<Integer> numbers) {
        Integer collector = numbers.stream().mapToInt(Integer::intValue).sum();
        return new Result<Integer>(collector);
    }

    private Result<Integer> multiply(List<Integer> numbers) {
        Integer collector = numbers.stream().mapToInt(Integer::intValue).reduce(1, Math::multiplyExact);
        return new Result<Integer>(collector);
    }

    private Result<List<Integer>> applyDouble(List<Integer> numbers) {
        List<Integer> collector = numbers.stream().map(n -> n*2).collect(Collectors.toList());
        return new Result<List<Integer>>(collector);
    }
}
