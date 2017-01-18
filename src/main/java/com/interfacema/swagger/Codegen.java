package com.interfacema.swagger;

import java.io.File;
import java.util.*;

import io.swagger.codegen.languages.AbstractCSharpCodegen ;
import io.swagger.models.Operation;
import io.swagger.codegen.*;

import static java.util.UUID.randomUUID;

public class Codegen extends AbstractCSharpCodegen  {
    private final String packageGuid = "{" + randomUUID().toString().toUpperCase() + "}";

    public Codegen() {
        super();

        outputFolder = "generated-code" + File.separator + this.getName();

        modelTemplateFiles.put("model.mustache", ".cs");
        apiTemplateFiles.put("controller.mustache", ".cs");

        // contextually reserved words
        setReservedWordsLowerCase(
                Arrays.asList("var", "async", "await", "dynamic", "yield")
        );

        cliOptions.clear();

        // CLI options
        addOption(CodegenConstants.PACKAGE_NAME,
                "C# package name (convention: Title.Case).",
                this.packageName);

        addOption(CodegenConstants.PACKAGE_VERSION,
                "C# package version.",
                this.packageVersion);

        addOption(CodegenConstants.SOURCE_FOLDER,
                CodegenConstants.SOURCE_FOLDER_DESC,
                sourceFolder);

        // CLI Switches
        addSwitch(CodegenConstants.SORT_PARAMS_BY_REQUIRED_FLAG,
                CodegenConstants.SORT_PARAMS_BY_REQUIRED_FLAG_DESC,
                this.sortParamsByRequiredFlag);

        addSwitch(CodegenConstants.USE_DATETIME_OFFSET,
                CodegenConstants.USE_DATETIME_OFFSET_DESC,
                this.useDateTimeOffsetFlag);

        addSwitch(CodegenConstants.USE_COLLECTION,
                CodegenConstants.USE_COLLECTION_DESC,
                this.useCollection);

        addSwitch(CodegenConstants.RETURN_ICOLLECTION,
                CodegenConstants.RETURN_ICOLLECTION_DESC,
                this.returnICollection);
    }

    @Override
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    @Override
    public String getName() {
    return "aspnetcore-custom";
    }

    @Override
    public String getHelp() {
        return "Generates an ASP.NET Core Web API server.";
    }

    @Override
    public void processOpts() {
        super.processOpts();

        additionalProperties.put("packageGuid", packageGuid);

        apiPackage = packageName + ".Controllers";
        modelPackage = packageName + ".Models";

        supportingFiles.add(new SupportingFile("NuGet.Config", "", "NuGet.Config"));
        supportingFiles.add(new SupportingFile("global.json", "", "global.json"));
        supportingFiles.add(new SupportingFile("build.sh.mustache", "", "build.sh"));
        supportingFiles.add(new SupportingFile("build.bat.mustache", "", "build.bat"));
        supportingFiles.add(new SupportingFile("README.mustache", "", "README.md"));
        supportingFiles.add(new SupportingFile("Solution.mustache", "", this.packageName + ".sln"));
        supportingFiles.add(new SupportingFile("Dockerfile.mustache", this.sourceFolder, "Dockerfile"));
        supportingFiles.add(new SupportingFile("gitignore", this.sourceFolder, ".gitignore"));
        supportingFiles.add(new SupportingFile("appsettings.json", this.sourceFolder, "appsettings.json"));

        supportingFiles.add(new SupportingFile("project.json.mustache", this.sourceFolder, "project.json"));
        supportingFiles.add(new SupportingFile("Startup.mustache", this.sourceFolder, "Startup.cs"));
        supportingFiles.add(new SupportingFile("Program.mustache", this.sourceFolder, "Program.cs"));
        supportingFiles.add(new SupportingFile("web.config", this.sourceFolder, "web.config"));

        supportingFiles.add(new SupportingFile("Project.xproj.mustache", this.sourceFolder, this.packageName + ".xproj"));

        supportingFiles.add(new SupportingFile("Properties" + File.separator + "launchSettings.json", this.sourceFolder + File.separator + "Properties", "launchSettings.json"));

        supportingFiles.add(new SupportingFile("wwwroot" + File.separator + "README.md", this.sourceFolder + File.separator + "wwwroot", "README.md"));
        supportingFiles.add(new SupportingFile("wwwroot" + File.separator + "index.html", this.sourceFolder + File.separator + "wwwroot", "index.html"));
        supportingFiles.add(new SupportingFile("wwwroot" + File.separator + "web.config", this.sourceFolder + File.separator + "wwwroot", "web.config"));

        apiTemplateFiles.put("controller-abstract.mustache", ".cs");
        apiTestTemplateFiles.put("controller-tests.mustache", ".cs");

        supportingFiles.clear();
    }

    @Override
    public String apiFileFolder() {
        return outputFolder + File.separator + sourceFolder + File.separator + "Controllers";
    }

    @Override
    public String modelFileFolder() {
        return outputFolder + File.separator + sourceFolder + File.separator + "Models";
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
    protected void processOperation(CodegenOperation operation) {
        super.processOperation(operation);

        // HACK: Unlikely in the wild, but we need to clean operation paths for MVC Routing
        if (operation.path != null) {
            String original = operation.path;
            operation.path = operation.path.replace("?", "/");
            if (!original.equals(operation.path)) {
                LOGGER.warn("Normalized " + original + " to " + operation.path + ". Please verify generated source.");
            }
        }

        // Converts, for example, PUT to HttpPut for controller attributes
        operation.httpMethod = "Http" + operation.httpMethod.substring(0, 1) + operation.httpMethod.substring(1).toLowerCase();
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
        if(fileName.contains(("Models")))
        {
            return true;
        }
        if(fileName.contains("Api") && !fileName.contains("Abstract"))
        {
            //don't overwrite implementations
            return false;
        }
        return super.shouldOverwrite(fileName);
    }
}