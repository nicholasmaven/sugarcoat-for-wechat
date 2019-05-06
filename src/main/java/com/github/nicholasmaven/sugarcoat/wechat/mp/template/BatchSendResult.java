package com.github.nicholasmaven.sugarcoat.wechat.mp.template;

import lombok.Data;

import java.util.List;

/**
 * Result holder include success count, total count and every single response
 *
 * @author mawen
 * @create 2019-03-11 16:53
 */
@Data
public class BatchSendResult {
    private final int total;
    private final int success;
    private final List<String> results;
}
