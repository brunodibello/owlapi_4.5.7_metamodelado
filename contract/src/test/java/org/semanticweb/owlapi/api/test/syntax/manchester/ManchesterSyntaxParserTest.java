package org.semanticweb.owlapi.api.test.syntax.manchester;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class ManchesterSyntaxParserTest extends TestBase {

    @Parameters
    public static Collection<Object[]> data() {
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        OWLDataProperty hasAge = df.getOWLDataProperty(IRI.create("http://example.org/hasAge"));
        OWLDatatype xsd_int = OWL2Datatype.XSD_INT.getDatatype(df);
        return Arrays.asList(
            //@formatter:off
            new Object[] { "hasAge exactly 1 xsd:int",  df.getOWLDataExactCardinality(1, hasAge, xsd_int) },
            new Object[] { "hasAge exactly 1",          df.getOWLDataExactCardinality(1, hasAge) }, 
            new Object[] { "hasAge min 1 xsd:int",      df.getOWLDataMinCardinality(1, hasAge, xsd_int) }, 
            new Object[] { "hasAge min 1",              df.getOWLDataMinCardinality(1, hasAge) }, 
            new Object[] { "hasAge max 1 xsd:int",      df.getOWLDataMaxCardinality(1, hasAge, xsd_int) }, 
            new Object[] { "hasAge max 1",              df.getOWLDataMaxCardinality(1, hasAge) });
            //@formatter:on
    }

    private String input;
    private Object expected;

    public ManchesterSyntaxParserTest(String input, Object expected) {
        this.input = input;
        this.expected = expected;
    }

    @Test
    public void testParseDataCardinalityExpression() throws OWLOntologyCreationException {
        OWLDataProperty hasAge = df.getOWLDataProperty(IRI.create("http://example.org/hasAge"));
        OWLOntology ont = m.createOntology();
        m.addAxiom(ont, df.getOWLDeclarationAxiom(hasAge));
        ManchesterOWLSyntaxClassExpressionParser parser = new ManchesterOWLSyntaxClassExpressionParser(df, checker(m));
        assertEquals(expected, parser.parse(input));
    }

    protected OWLEntityChecker checker(OWLOntologyManager manager) {
        BidirectionalShortFormProviderAdapter adapter = new BidirectionalShortFormProviderAdapter(manager
            .getOntologies(), new SimpleShortFormProvider());
        OWLEntityChecker checker = new ShortFormEntityChecker(adapter);
        return checker;
    }
}
