package br.com.bancosimplificado.math.useful;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class CpfCnpjValidation implements ConstraintValidator<ValidDocument, String> {

    private final Validator validator;

    public CpfCnpjValidation() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public void initialize(ValidDocument constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        if (value.length() == 11) {
            return validator.validate(new DummyCpfEntity(value)).isEmpty();
        } else if (value.length() == 14) {
            return validator.validate(new DummyCnpjEntity(value)).isEmpty();
        }

        return false;
    }

    private static class DummyEntity {
        private String document;
    }

    private static class DummyCpfEntity extends DummyEntity {
        @CPF
        private String document;

        public DummyCpfEntity(String document) {
            this.document = document;
        }
    }

    private static class DummyCnpjEntity extends DummyEntity {
        @CNPJ
        private String document;

        public DummyCnpjEntity(String document) {
            this.document = document;
        }
    }

}
