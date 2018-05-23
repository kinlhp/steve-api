package com.kinlhp.steve.api.dominio.conversao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

@Converter(autoApply = true)
public class ImplementacaoConversorMesReferente
		implements AttributeConverter<YearMonth, Date>, Serializable {

	private static final long serialVersionUID = 1045792152700877734L;

	@Override
	public Date convertToDatabaseColumn(YearMonth yearMonth) {
		final LocalDate localDate = LocalDate.from(yearMonth.atDay(1)
				.atStartOfDay(ZoneId.systemDefault()));
		return Date.valueOf(localDate);
	}

	@Override
	public YearMonth convertToEntityAttribute(Date date) {
		return YearMonth
				.from(date.toLocalDate().atStartOfDay(ZoneId.systemDefault()));
	}
}
