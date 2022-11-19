package com.adidas.backend.publicservice.listeners;

/*
public class KafkaSaleDeserializer extends ErrorHandlingDeserializer implements Deserializer {
    
    @Override
    public void configure(Map<String, ?> map, boolean b) {}

    @Override
    public Sale deserialize(String s, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new String(bytes,   
                   StandardCharsets.UTF_8), MessageDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when  
                      deserializing byte[] to messageDto");
        }
    }

    @Override
    public void close() {}    
}
*/