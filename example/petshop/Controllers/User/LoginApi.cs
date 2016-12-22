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

namespace Swagger.Petshop.Controllers.User
{
    public class LoginApi : Abstract.LoginApi
    { 

        public async Task<IActionResult> GetAsync([FromQuery]string username, [FromQuery]string password)
        {
            throw new NotImplementedException();
        }
    }
}
