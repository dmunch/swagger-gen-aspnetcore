package com.interfacema.swagger;

import java.util.*;

import io.swagger.codegen.languages.AspNetCoreServerCodegen ;
import io.swagger.models.Operation;
import io.swagger.codegen.*;

public class Codegen extends AspNetCoreServerCodegen  {

    public Codegen() {
        super();
    }

    @Override
    public String getName() {
    return "aspnetcore-custom";
    }

    @Override
    public void processOpts() {
        super.processOpts();

        apiTemplateFiles.put("controller-abstract.mustache", ".cs");
        apiTestTemplateFiles.put("controller-tests.mustache", ".cs");

        supportingFiles.clear();
    }

    @Override
    public void addOperationToGroup(String tag, String resourcePath, Operation operation, CodegenOperation co, Map<String, List<CodegenOperation>> operations) {
        //Inspired by:
        //https://www.bountysource.com/issues/36534240-server-spring-operation-groups-configuration

        String basePath = resourcePath;

        //remove starting or trailing slash
        if (basePath.startsWith("/")) {
            basePath = basePath.substring(1);
        }
        if(basePath.endsWith(("/"))) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }

        //remove route parameters
        String fileName = basePath
                .replaceAll("[{][^{}]+[}]", "")
                .replace("//", "/");

        //remove trailing slash again after having removed route parameters
        if(fileName.endsWith(("/"))) {
            fileName = fileName.substring(0, fileName.length() - 1);
        }

        String[] parts = fileName.split("/");
        StringBuilder builder = new StringBuilder();
        for(int pos = 0; pos < parts.length; pos++) {
            String part = parts[pos];

            for(String innerPart: part.split("-")) {
                builder.append(initialCaps(innerPart));
            }

            if(pos == 0 && parts.length > 1) {
                builder.append("/");
            }
        }

        fileName = initialCaps(builder.toString());

        List<CodegenOperation> opList = operations.get(fileName);
        if (opList == null) {
            opList = new ArrayList<CodegenOperation>();
            operations.put(fileName, opList);
        }

        opList.add(co);
        co.operationId = initialCaps(co.httpMethod.toLowerCase());
        co.baseName = fileName;
    }

    @Override
    //gives us the name of the file
    public String toApiName(String name) {
        //name is the name we put into the operations map in addOperationToGroup
        if (name.length() == 0) {
            return "Default";
        }

        String[] split = name.split("/");
        return initialCaps(split[split.length - 1]);
    }

    @Override
    public String toApiFilename(String name) {
        //complete path is conserved
        return initialCaps(name) + "Api";
    }

    @Override
    public String toApiTestFilename(String name) {
        //complete path is conserved
        return "Api_" + name.replace("/", "_") + "Test";
    }

    @Override
    public String apiFilename(String templateName, String tag) {
        if(templateName.contains("abstract")) {
            String suffix = apiTemplateFiles().get(templateName);
            return apiFileFolder() + "/Abstract/" + toApiFilename(tag) + suffix;
        } else {
            return super.apiFilename(templateName, tag);
        }
    }

    @Override
    public Map<String, Object> postProcessOperations(Map<String, Object> operations)
    {
        super.postProcessOperations(operations);

        Map<String, Object> objs = (Map<String, Object>) operations.get("operations");
        List<CodegenOperation> ops = (List<CodegenOperation>)objs.get("operation");

        /*
        for(CodegenOperation op: ops) {
            // Converts, for example, PUT to HttpPut for controller attributes
            //op.httpMethod = "Http" + op.httpMethod.substring(0, 1) + op.httpMethod.substring(1).toLowerCase();
            op.httpMethod = op.httpMethod.substring(0, 1) + op.httpMethod.substring(1).toLowerCase();
        }*/

        String fileName = ops.get(0).baseName;
        String[] split = fileName.split("/");

        String namespace = "";

        if(split.length > 1) {
            namespace = "." + initialCaps(split[0]);
        }

        objs.put("namespace", namespace);
        objs.put("testclassname", fileName.replace("/", "_"));

        return objs;
    }

    @Override
    public boolean shouldOverwrite(String fileName)
    {
        if(fileName.contains("Api") && !fileName.contains("Abstract"))
        {
            //don't overwrite implementations
            return false;
        }
        return super.shouldOverwrite(fileName);
    }
}