package com.kapital.onlinepaymentgateway.kapitalecommerce.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.kapital.PurchaseResponse;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.kapital.StatusResponse;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.kapital.TKKPG_Response;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Component
public class XmlUtil {
    XmlMapper mapper = new XmlMapper();
    @SneakyThrows
    public String write(Object o) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mapper.writerWithDefaultPrettyPrinter().writeValue(stream, o);
        return stream.toString();
    }

    @SneakyThrows
    public TKKPG_Response<PurchaseResponse> readPurchaseResponse(InputStream stream) {
        return mapper.readValue(stream, new TypeReference<>() {});
    }

    @SneakyThrows
    public TKKPG_Response<StatusResponse> readStatusResponse(InputStream stream) {
        return mapper.readValue(stream, new TypeReference<>() {});
    }
}
