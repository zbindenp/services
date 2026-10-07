package ch.sachi.services.main;

import java.util.List;

public record MainResult(String traceid, List<ProductInfoDto> products, List<CustomerInfo> customers) {
}
