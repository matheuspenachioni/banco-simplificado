package br.com.bancosimplificado.math.useful;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import br.com.bancosimplificado.math.model.Users;
import br.com.bancosimplificado.math.repository.UserRepository;

public class UserDeserializer extends JsonDeserializer<Users> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Users deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        UUID id = UUID.fromString(jp.getValueAsString());
        return userRepository.findById(id).orElse(null);
    }
    
}
