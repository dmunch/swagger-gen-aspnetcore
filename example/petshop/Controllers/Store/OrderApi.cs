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

namespace Swagger.Petshop.Controllers.Store
{
    public class OrderApi : Abstract.OrderApi
    { 

        public async Task DeleteAsync([FromRoute]long? orderId)
        {
            throw new NotImplementedException();
        }


        public async Task<IActionResult> GetAsync([FromRoute]long? orderId)
        {
            throw new NotImplementedException();
        }


        public async Task<IActionResult> PostAsync([FromBody]Order body)
        {
            throw new NotImplementedException();
        }
    }
}
