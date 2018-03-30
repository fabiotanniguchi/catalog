package br.unicamp.sindo.catalog.catalogcore.features;

import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = { "pretty", "html:target/cucumber"})
@Transactional
public class CatalogCoreFeaturesTest {

}
