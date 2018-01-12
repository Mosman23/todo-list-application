package net.mhabib.todo.controller;

import io.swagger.annotations.Api;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import static com.google.common.collect.ImmutableMap.of;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(path = "/version")
@Api("Version Controller")
public class VersionController {

    @RequestMapping(method = GET)
    @ResponseBody
    public ResponseEntity<Map<String, String>> getVersion() {
        return new ResponseEntity<>(of("version", "1.0"), OK);
    }
}
