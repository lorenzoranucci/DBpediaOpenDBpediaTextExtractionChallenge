package org.dbpedia.extraction.nif;

import lombok.Getter;
import lombok.Setter;
import org.dbpedia.extraction.nif.annotation.Type;

import static org.dbpedia.extraction.nif.specification.NIFSpec.NAMESPACE;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_BEGININDEX;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_ENDINDEX;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_REFERENCE_CONTEXT;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_STRUCTURE;
import static org.dbpedia.extraction.nif.specification.NIFSpec.RDF_XML_LITERAL;

@Getter
@Setter
@Type(name = NIF_PROPERTY_STRUCTURE)
public class NIFStructure {

    @Type(name = NAMESPACE)
    private String namespace;

    @Type(name = NIF_PROPERTY_BEGININDEX)
    private Integer beginIndex;

    @Type(name = NIF_PROPERTY_ENDINDEX)
    private Integer endIndex;

    @Type(name = NIF_PROPERTY_REFERENCE_CONTEXT)
    private String referenceContext;

    @Type(name = RDF_XML_LITERAL)
    private String literal;
}
