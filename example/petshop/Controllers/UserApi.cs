using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using Swagger.Petshop.Models;
using Microsoft.AspNetCore.JsonPatch;
using Microsoft.AspNetCore.Http;

namespace Swagger.Petshop.Controllers
{
    public class UserApi : Abstract.UserApi
    { 

        public async Task DeleteAsync([FromRoute]string username)
        {
            throw new NotImplementedException();
        }


        public async Task<IActionResult> GetAsync([FromRoute]string username)
        {
            throw new NotImplementedException();
        }


        public async Task PostAsync([FromBody]User body)
        {
            throw new NotImplementedException();
        }


        public async Task PutAsync([FromRoute]string username, [FromBody]User body)
        {
            throw new NotImplementedException();
        }
    }
}
