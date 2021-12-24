import requests
import json
import sys

DOMAIN = 'adb-3305711601758284.4.azuredatabricks.net'
# TOKEN = 'dapi76e6ef0c81459fca303f219c61b4fa9c'
TOKEN = 'dapif5ac6311e0fb3297abc172065eed873c'
BASEURL = "https://%s/api/2.0/jobs/" % (DOMAIN)

if len(sys.argv) < 3:
    job_id = "5560"
    wellbore_ID="1234"
else:
    job_id = sys.argv[1]
    wellbore_ID = sys.argv[2]

    job_id = job_id.strip('"')
    wellbore_ID = wellbore_ID.strip('"')


def run_job(job_id,wellbore_ID):
    response = requests.post(BASEURL+'run-now',headers={'Authorization': 'Bearer %s' % TOKEN},
    json = {
        "job_id": "{}".format(job_id),
        "notebook_params": { "wellbore_id":wellbore_ID }})
    content = json.loads(response.content)
    print(content)
    print("Job queued for running!")

# job_id=2907
run_job(job_id,wellbore_ID)