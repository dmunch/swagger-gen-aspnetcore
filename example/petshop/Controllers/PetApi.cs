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
    public class PetApi : Abstract.PetApi
    { 

        public async Task DeleteAsync([FromRoute]long? petId, [FromHeader]string apiKey)
        {
            throw new NotImplementedException();
        }


        public async Task<IActionResult> GetAsync([FromRoute]long? petId)
        {
            throw new NotImplementedException();
        }


        public async Task PostAsync([FromBody]Pet body)
        {
            throw new NotImplementedException();
        }


        public async Task PostAsync([FromRoute]long? petId, [FromForm]string name, [FromForm]string status)
        {
            throw new NotImplementedException();
        }


        public async Task PutAsync([FromBody]Pet body)
        {
            throw new NotImplementedException();
        }
    }
}
