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

namespace Swagger.Petshop.Controllers.Pet
{
    public class FindByTagsApi : Abstract.FindByTagsApi
    { 

        public async Task<IActionResult> GetAsync([FromQuery]List<string> tags)
        {
            throw new NotImplementedException();
        }
    }
}
