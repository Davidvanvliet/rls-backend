package nl.rls.composer.xml.mapper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import info.taf_jsg.schemes.TransportOperationalIdentifiers;

public class TransportOperationalIdentifiersXmlMapper {
	public static TransportOperationalIdentifiers map(nl.rls.composer.domain.message.TrainCompositionMessage tcm) {
		DozerBeanMapper mapper = new DozerBeanMapper();
		BeanMappingBuilder mappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(nl.rls.composer.domain.message.TrainCompositionMessage.class,
						info.taf_jsg.schemes.TransportOperationalIdentifiers.class)
								.fields("company.code", "company");

			}
		};
		mapper.addMapping(mappingBuilder);
		TransportOperationalIdentifiers xmlToi = mapper.map(tcm, TransportOperationalIdentifiers.class);
		return xmlToi;
	}
}
