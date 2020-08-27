package com.simpledev.springboot_comm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.*;

/***
 * 测试了下，没有生效，不知道原因。。。
 */
@RestController
public class JSR303Controller {

    @GetMapping("/mustNull/{m}")
    public String mustNull(@Null @PathVariable String m) {
        return "mustNull";
    }

    @GetMapping("/notNull/{m}")
    public String cannotNull(@NotNull @PathVariable String m) {
        return "notNull";
    }

    @GetMapping("/min/{m}")
    public String min(@Valid @Min(9) @PathVariable int m) {
        return m + "";
    }

    @GetMapping("/max")
    public String max(@Max(9) @RequestParam int m) {
        return m + ";";
    }

    @GetMapping("/range/{m}")
    public String range(@Size(min = 7, max = 9) @PathVariable int m) {
        return m + "";
    }

    @GetMapping("/email/{m}")
    public String email(@Pattern(regexp = ".*@.*") @PathVariable String m) {
        return m;
    }
}
