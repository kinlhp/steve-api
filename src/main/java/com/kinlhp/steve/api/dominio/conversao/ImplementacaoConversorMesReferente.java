package com.kinlhp.steve.api.dominio.conversao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

@Converter(autoApply = true)
public class ImplementacaoConversorMesReferente
		implements AttributeConverter<YearMonth, LocalDate>, Serializable {

	private static final long serialVersionUID = 5041719727138404385L;

	@Override
	public LocalDate convertToDatabaseColumn(YearMonth yearMonth) {
		return LocalDate.from(yearMonth.atDay(1)
				.atStartOfDay(ZoneId.systemDefault()));
	}

	@Override
	public YearMonth convertToEntityAttribute(LocalDate localDate) {
		return YearMonth.from(localDate.atStartOfDay(ZoneId.systemDefault()));
	}
}
