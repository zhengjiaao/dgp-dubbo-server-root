package com.dist.base.utils.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collections;

/**
 * 使feign支持传输文件数组的编码器
 *
 * @author yujx
 * @date 2019/11/13 16:36
 */
class FormEncoderExt extends FormEncoder {

    FormEncoderExt(Encoder encoder) {
        super(encoder);
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (bodyType.equals(File[].class)) {
            object = Collections.singletonMap("files", object);
            super.encode(object, MAP_STRING_WILDCARD, template);
            return;
        }
        super.encode(object, bodyType, template);
    }
}
