package com.example.demo.graphql.provider;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.example.demo.graphql.fetcher.GraphQLFetcher;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.net.URL;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SuppressWarnings("UnstableApiUsage")
@Component
public class GraphQLProvider {

  private GraphQL graphQL;

  @Autowired
  private GraphQLFetcher graphQLFetcher;

  @Bean
  public GraphQL graphQL() {
    return graphQL;
  }

  @PostConstruct
  public void init() throws IOException {
    URL url = Resources.getResource("schema.graphqls");
    String sdl = Resources.toString(url, Charsets.UTF_8);
    GraphQLSchema graphQLSchema = buildSchema(sdl);
    this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  private GraphQLSchema buildSchema(String sdl) {
    TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(sdl);
    return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, buildWiring());
  }

  private RuntimeWiring buildWiring() {
    return RuntimeWiring.newRuntimeWiring()
        .type(newTypeWiring("Query").dataFetcher("allUsers", graphQLFetcher.getAllUsers()))
        .type(newTypeWiring("Mutation").dataFetcher("addUser", graphQLFetcher.addUser()))
        .build();
  }

}
