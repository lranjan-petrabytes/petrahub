import requests
import json

DOMAIN = 'adb-3305711601758284.4.azuredatabricks.net'
TOKEN = 'dapi76e6ef0c81459fca303f219c61b4fa9c'

def getClusterState(cluster_id):
    # clusters = getClusters()
    # cluster_id = '0304-081914-quits100'
    BASEURL = "https://%s/api/2.0/clusters/" % (DOMAIN)
    response = requests.get(BASEURL+'get?cluster_id={}'.format(cluster_id),
    headers = {'Authorization': 'Bearer %s' % TOKEN})
    text = json.loads(response.content)
    # print(text)
    state = text['state']
    return(state)

cluster_id = '0304-081914-quits100'
cluster_state = getClusterState(cluster_id)
print(cluster_state)