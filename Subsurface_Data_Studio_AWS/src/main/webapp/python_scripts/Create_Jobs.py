import json
import requests
import sys

if len(sys.argv) < 3:
    Notebook = "/Repos/sashi@petrabytes.com/bluegridz_py_dev/databricks_notebooks/Databricks_Training/Use_Cases/Upstream_Analytics/Subsurface_Lakehouse/calculations/calculation_test1"
    Cluster_ID="0304-081914-quits100"
else:
    Notebook = sys.argv[1]
    Cluster_ID = sys.argv[2]

    Notebook = Notebook.strip('"')
    Cluster_ID = Cluster_ID.strip('"')



DOMAIN = 'adb-3305711601758284.4.azuredatabricks.net'
TOKEN = 'dapif5ac6311e0fb3297abc172065eed873c'

BASEURL = "https://%s/api/2.0/jobs/" % (DOMAIN)
print(BASEURL)

def job_create(Notebook,Cluster_ID):
    response = requests.post(BASEURL+'/create',headers={'Authorization': 'Bearer %s' % TOKEN},
    json = {
        "name": "Calculation_Jobs",

        "existing_cluster_id": Cluster_ID
        ,
          "notebook_task": {
              "notebook_path": Notebook

          },
        "email_notifications": {  },
        "max_concurrent_runs": 10
        }    )
    content = json.loads(response.content)
    print(content)

job_create(Notebook,Cluster_ID)