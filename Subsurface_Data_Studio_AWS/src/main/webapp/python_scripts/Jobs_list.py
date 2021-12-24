import requests
import json

DOMAIN = 'adb-3305711601758284.4.azuredatabricks.net'
TOKEN = 'dapi76e6ef0c81459fca303f219c61b4fa9c'

BASEURL = "https://%s/api/2.0/jobs/" % (DOMAIN)
response = requests.get(BASEURL+'/list',headers={'Authorization': 'Bearer %s' % TOKEN},)
content = json.loads(response.content)
print(content)
