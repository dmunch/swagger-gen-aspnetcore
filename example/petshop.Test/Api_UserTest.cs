using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.TestHost;
using Microsoft.Extensions.DependencyInjection;
using Moq;
using Newtonsoft.Json;
using SwaviApp.Data.Repositories;
using SwaviApp.WebApi.Business;
using SwaviApp.WebApi.Models;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace Swagger.Petshop.Test
{
    using Swagger.Petshop.Controllers;

    public class Api_UserTests : IDisposable
    {
        private readonly TestServer testServer;
        private readonly HttpClient client;

        public Api_UserTests()
        {
            testServer = new TestServer(new WebHostBuilder()
                .UseStartup<Startup>()
                .UseEnvironment("Testing")
                .ConfigureServices(services =>
                    {
                        //TODO setup your mocked services here
                    })
            );

            client = testServer.CreateClient();
        }

        public void Dispose()
        {
            client.Dispose();
            testServer.Dispose();
        }

        [Fact]
        public async Task Delete_Should_Do_Something_Useful_username()
        {
            throw new NotImplementedException();
        }
        [Fact]
        public async Task Get_Should_Do_Something_Useful_username()
        {
            throw new NotImplementedException();
        }
        [Fact]
        public async Task Post_Should_Do_Something_Useful_body()
        {
            throw new NotImplementedException();
        }
        [Fact]
        public async Task Put_Should_Do_Something_Useful_username_body()
        {
            throw new NotImplementedException();
        }
    }
}
