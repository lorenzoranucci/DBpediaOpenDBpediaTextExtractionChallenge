package org.dbpedia.extraction.parser;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.dbpedia.extraction.nif.NIF;
import org.dbpedia.extraction.nif.NIFContext;
import org.dbpedia.extraction.nif.NIFParagraph;
import org.dbpedia.extraction.nif.NIFPhrase;
import org.dbpedia.extraction.nif.NIFSection;
import org.dbpedia.extraction.nif.NIFStructure;
import org.dbpedia.extraction.nif.NIFTitle;
import org.dbpedia.extraction.nif.NIFWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_CONTEXT;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_PARAGRAPH;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_PHRASE;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_SECTION;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_STRUCTURE;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_TITLE;
import static org.dbpedia.extraction.nif.specification.NIFSpec.NIF_PROPERTY_WORD;

public class NIFParser extends Parser {

    private String nif;

    private NIFContext context;

    private List<NIFParagraph> paragraphs;

    private List<NIFPhrase> phases;

    private List<NIFSection> sections;

    private List<NIFStructure> structures;

    private List<NIFTitle> titles;

    private List<NIFWord> words;

    public NIFParser(String nif) {

        Objects.requireNonNull(nif);

        this.nif = nif;
        this.context = new NIFContext();
        this.paragraphs = new ArrayList<>();
        this.phases = new ArrayList<>();
        this.sections = new ArrayList<>();
        this.structures = new ArrayList<>();
        this.titles = new ArrayList<>();
        this.words = new ArrayList<>();

    }

    public Model getModel() {
        return super.getModel("TTL", nif);
    }

    private void fill() {

        Model model = getModel();

        StmtIterator stmtIterator = model.listStatements();

        while (stmtIterator.hasNext()) {
            Statement stm = stmtIterator.nextStatement();
            Resource resource = stm.getSubject().asResource();
            String type = resource.getPropertyResourceValue(RDF.type).getURI();
            if (NIF_PROPERTY_PHRASE.equals(type)) {
                extractNIFPhase(phases, resource, model);
            } else if (NIF_PROPERTY_WORD.equals(type)) {
                extractNIFWord(words, resource, model);
            } else if (NIF_PROPERTY_TITLE.equals(type)) {
                extractTitle(titles, resource, model);
            } else if (NIF_PROPERTY_STRUCTURE.equals(type)) {
                extractStructure(structures, resource, model);
            } else if (NIF_PROPERTY_SECTION.equals(type)) {
                extractSection(sections, resource, model);
            } else if (NIF_PROPERTY_PARAGRAPH.equals(type)) {
                extractParagraph(paragraphs, resource, model);
            } else if (NIF_PROPERTY_CONTEXT.equals(type)) {
                extractContext(context, resource, model);
            }
        }

    }

    public NIF getNIF() {

        fill();

        NIF result = new NIF();
        result.setContext(this.context);
        result.setPhases(this.phases);
        result.setParagraphs(this.paragraphs);
        result.setSections(this.sections);
        result.setTitles(this.titles);
        result.setWords(this.words);


        return result;
    }
}
