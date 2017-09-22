# Swagger Codegen ASP.NET Core

## Overview

This is an alternative implementation of the [existing ASP.NET Core codegen](https://github.com/swagger-api/swagger-codegen/blob/master/modules/swagger-codegen/src/main/java/io/swagger/codegen/languages/AspNetCoreServerCodegen.java) 
which addressed some practical issues we had during the implementation of our REST API.

Most notably:
- It creates abstract base classes with the necessary attributes. Implementing your controller means sub-classing 
the abstract base classes. Advantage: When your schema changes, only the abstract base class definitions get overwritten. 
The C# compiler gives your errors for methods not implemented yet. 
- All router handlers are `async` by default.
- Route handlers don't get grouped by tag but by path. Now you can use the tags again for grouping paths in Swagger UI.
- It doesn't use [Swashbuckle](https://github.com/domaindrivendev/Ahoy). What's the point in creating the schema for Swagger UI from the code when the code was itself already created by the schema? 
- It creates stubs for tests as well!

## Usage

- Use `swagger-codegen-cli`
- Use [`swagger-codegen-maven-plugin`](https://github.com/swagger-api/swagger-codegen/tree/master/modules/swagger-codegen-maven-plugin) with the provided [pom.xml](example/petshop/pom.xml) as an example. Run `mvn compile` on the command line to generate your C# code. Yeah I think that's funny, too!

## Example

Check out the obligatory [petshop example](example/petshop) in the repository.

## Build

swagger-gen-aspnetcore now depends on version 2.2.1 of [swagger-codegen](https://github.com/swagger-api/swagger-codegen/). Go into the source directory and run `mvn install` to build and install
swagger-gen-aspnetcore into your local `.m2` directory.

Packaged versions starting from 0.9.1 are available on Bintray.

## What's Swagger?
The goal of Swagger™ is to define a standard, language-agnostic interface to REST APIs which allows both humans and computers to discover and understand the capabilities of the service without access to source code, documentation, or through network traffic inspection. When properly defined via Swagger, a consumer can understand and interact with the remote service with a minimal amount of implementation logic. Similar to what interfaces have done for lower-level programming, Swagger removes the guesswork in calling the service.


Check out [OpenAPI-Spec](https://github.com/OAI/OpenAPI-Specification) for additional information about the Swagger project, including additional libraries with support for other languages and more. 