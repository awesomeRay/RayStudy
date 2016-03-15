import boto3

clusterName = "AUTO_CLUSTER_" + "play-cube-2015/12/14" 

client = boto3.client('emr')
client.run_job_flow(
    Name=clusterName,
    LogUri='s3://fengxinzi-bucket01/emr/',
    AmiVersion='3.9.0',
    Instances={
        'MasterInstanceType': 'm3.xlarge',
        'SlaveInstanceType': 'm3.xlarge',
        'InstanceCount': 3,
        'Ec2KeyName': 'OTT-bestadmin',
        'KeepJobFlowAliveWhenNoSteps': True,
        'TerminationProtected': False,
        'HadoopVersion': '2.4.0',
        'Ec2SubnetId': 'subnet-9516faf0',
        'AdditionalMasterSecurityGroups': [
            'sg-fa2bc79f',
        ],
        'AdditionalSlaveSecurityGroups': [
            'sg-fa2bc79f',
        ]
    },
    Steps=[
        {
            'Name': 'daily',
            'ActionOnFailure': 'TERMINATE_JOB_FLOW',
#             'ActionOnFailure': 'CANCEL_AND_WAIT',
            'HadoopJarStep': {
                'Jar': 's3://cn-north-1.elasticmapreduce/libs/script-runner/script-runner.jar',
                'Args': [
                    's3://fengxinzi-bucket01/emr_conf/compute-step/run_single.sh',
                    'jobs/ott/play/play_daily_usercount_cube.py',
                    '2015-01-01', '2015-12-14',
                ]
            }
        },
        {
            'Name': 'hourly',
            'ActionOnFailure': 'TERMINATE_JOB_FLOW',
#             'ActionOnFailure': 'CANCEL_AND_WAIT',
            'HadoopJarStep': {
                'Jar': 's3://cn-north-1.elasticmapreduce/libs/script-runner/script-runner.jar',
                'Args': [
                    's3://fengxinzi-bucket01/emr_conf/compute-step/run_single.sh',
                    'jobs/ott/play/play_hourly_usercount_cube.py',
                    '2015-01-01', '2015-12-14',
                ]
            }
        },
        {
            'Name': 'pn',
            'ActionOnFailure': 'TERMINATE_JOB_FLOW',
#             'ActionOnFailure': 'CANCEL_AND_WAIT',
            'HadoopJarStep': {
                'Jar': 's3://cn-north-1.elasticmapreduce/libs/script-runner/script-runner.jar',
                'Args': [
                    's3://fengxinzi-bucket01/emr_conf/compute-step/run_single.sh',
                    'jobs/ott/play/play_daily_play_minute_pn_cube.py',
                    '2015-01-01', '2015-12-14',
                ]
            }
        }
    ],
    BootstrapActions=[
        {
            'Name': 'Install Hive Site Configuration',
            'ScriptBootstrapAction': {
                'Path': 's3://cn-north-1.elasticmapreduce/libs/hive/hive-script',
                'Args': [
                    '--base-path','s3://cn-north-1.elasticmapreduce/libs/hive','--install-hive-site','--hive-site=s3://fengxinzi-bucket01/emr_conf/hive-site-formal.xml','--hive-versions','latest'
                ]
            }
        },
    ],
    NewSupportedProducts=[
        {
            'Name': 'Hive'
        }
    ],
    VisibleToAllUsers=True,
    JobFlowRole='EMR_EC2_DefaultRole',
    ServiceRole='EMR_DefaultRole',
    Tags=[
        {
            'Key': 'name',
            'Value': 'HiveClusterOnce'
        },
    ]
)
