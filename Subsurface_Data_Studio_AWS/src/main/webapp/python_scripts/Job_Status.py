# Copyright © 2021 Petrabytes Corporation. All rights reserved
#=====================================
# Author  : Lalan Ranjan
# @Email  : lranjan@petrabyutes.com
# @Date   : 01-07-21
#=====================================

import requests
import json
import sys

if len(sys.argv) < 2:
    run_id = "5693"
else:
    run_id = sys.argv[1]

    run_id = run_id.strip('"')

DOMAIN = 'adb-3305711601758284.4.azuredatabricks.net'
TOKEN = 'dapi76e6ef0c81459fca303f219c61b4fa9c'
BASEURL = "https://%s/api/2.0/jobs/" % (DOMAIN)

def job_output(run_id):
    response = requests.get(BASEURL+'/runs/get-output?run_id={}'.format(run_id),headers={'Authorization': 'Bearer %s' % TOKEN},)
    content = json.loads(response.content)
    # print(content)
    print(content['metadata']['state']['result_state'])


# run_id="3429"
job_output(run_id)