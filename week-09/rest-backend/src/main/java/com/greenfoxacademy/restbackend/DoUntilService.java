package com.greenfoxacademy.restbackend;

import com.greenfoxacademy.restbackend.model.dto.Result;
import com.greenfoxacademy.restbackend.util.DoUntilActions;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class DoUntilService {
    public Result doActionUntil(String action, Integer until) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Result<Integer> result = new Result<>();

        DoUntilActions actions = new DoUntilActions();
        Method method = actions.getClass().getMethod(action, int.class, int.class);

        int collector = 0;
        for (int i = 0; i < until; i++) {
                collector = (int)method.invoke(actions, collector, i+1);
        }
        result.setResult(collector);
        return result;
    }
}
