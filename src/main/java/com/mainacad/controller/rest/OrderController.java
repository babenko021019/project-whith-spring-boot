package com.mainacad.controller.rest;

import com.mainacad.model.Order;
import com.mainacad.service.OrderService;
import com.mainacad.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("order")
@Slf4j
@Profile("rest")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    MapperUtil mapperUtil;

    @PutMapping
    public ResponseEntity save(@RequestBody String requestBody) {
        Order savedOrder = orderService.save(mapperUtil.toOrder(mapperUtil.toOrderDTO(requestBody)));
        if (savedOrder == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(mapperUtil.jsonOrderDTOSimpleFormat(savedOrder), HttpStatus.OK);
    }

    @GetMapping({"", "{id}"})
    public ResponseEntity getOrder(@PathVariable(required = false) Integer id) {
        if (id != null) {
            Order order = orderService.getById(id);
            if (order == null) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(mapperUtil.jsonOrderDTOSimpleFormat(order), HttpStatus.OK);
        } else {
            return new ResponseEntity(mapperUtil.jsonOrderDTOSimpleFormatList(orderService.getAll()), HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody Order order) {
        try {
            orderService.delete(order);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Bad order params");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable Integer id) {
        try {
            orderService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Wrong id = %d", id));
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("by-cart/{cartId}")
    public ResponseEntity getAllByCart(Integer cartId) {
        return new ResponseEntity(mapperUtil.jsonOrderDTOSimpleFormatList(orderService.getAllByCart(cartId)), HttpStatus.OK);
    }

    @GetMapping("items-by-cart/{cartId}")
    public ResponseEntity getAllDTOByCard(@PathVariable Integer cartId) {
        return new ResponseEntity(mapperUtil.jsonOrderDTOItemFormatList(orderService.getAllByCart(cartId)), HttpStatus.OK);
    }

    @PostMapping("update-amount")
    public ResponseEntity updateAmount(@RequestBody String body) {
        Map<String, Object> map = new JacksonJsonParser().parseMap(body);
        int updatedRows = orderService.updateAmount((Integer) map.get("orderId"), (Integer) map.get("amount"));
        if (updatedRows < 1) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
