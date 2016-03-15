import boto3

clusterName = "AUTO_CLUSTER_" + "computint-all qos+play+active" 

client = boto3.client('emr')
client.run_job_flow(
    Name=clusterName,
    LogUri='s3://fengxinzi-bucket01/emr/emr-4.2-logs',
    ReleaseLabel='emr-4.2.0',
    Instances={
        'MasterInstanceType': 'c3.2xlarge',
        'SlaveInstanceType': 'c3.2xlarge',
        'InstanceCount': 3,
        'Ec2KeyName': 'OTT-bestadmin',
        'KeepJobFlowAliveWhenNoSteps': False,
        'TerminationProtected': False,
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
            'Name': 'Step1: install tez',
            'ActionOnFailure': 'TERMINATE_JOB_FLOW',
            'HadoopJarStep': {
                'Jar': 's3://cn-north-1.elasticmapreduce/libs/script-runner/script-runner.jar',
                'Args': [
                    's3://fengxinzi-bucket01/emr_conf/init-step/tez/install-tez.sh'
                ]
            }
        },
        
        {
            'Name': 'qos',
            'ActionOnFailure': 'TERMINATE_JOB_FLOW',
            'HadoopJarStep': {
                'Jar': 's3://cn-north-1.elasticmapreduce/libs/script-runner/script-runner.jar',
                'Args': [
                    's3://fengxinzi-bucket01/emr_conf/compute-step/run.sh',
                    '-j', 'ott.qos',
                    '-s', '2016-01-16',
                    '-e', '2016-01-24'
                ]
            }
        },
        {
            'Name': 'play.play',
            'ActionOnFailure': 'TERMINATE_JOB_FLOW',
            'HadoopJarStep': {
                'Jar': 's3://cn-north-1.elasticmapreduce/libs/script-runner/script-runner.jar',
                'Args': [
                    's3://fengxinzi-bucket01/emr_conf/compute-step/run.sh',
                    '-j', 'ott.play.play',
                    '-x', 'ott.play.play_req_daily_data',
                    '-s', '2016-01-16',
                    '-e', '2016-01-24'
                ]
            }
        },
        {
            'Name': 'active',
            'ActionOnFailure': 'TERMINATE_JOB_FLOW',
            'HadoopJarStep': {
                'Jar': 's3://cn-north-1.elasticmapreduce/libs/script-runner/script-runner.jar',
                'Args': [
                    's3://fengxinzi-bucket01/emr_conf/compute-step/run.sh',
                    '-j', 'ott.active',
                    '-s', '2016-01-16',
                    '-e', '2016-01-24'
                ]
            }
        },
        
    ],
    Applications=[
        {
            'Name': 'Hive'
        }
    ],
    Configurations=[
        {
            "Classification": "mapred-site",
            "Properties": {
                "mapreduce.map.output.compress": "true",
                "mapreduce.map.output.compress.codec": "org.apache.hadoop.io.compress.SnappyCodec",
                "mapreduce.input.fileinputformat.input.dir.recursive": "true",
                "mapred.child.java.opts":"-Xmx2048m"
            }
        },
        { 
            "Classification":"hadoop-env",
            "Properties":{ 

            },
            "Configurations":[ 
                { 
                    "Classification":"export",
                    "Properties":{ 
                        "HADOOP_HEAPSIZE": "2048"
                    }
                }
            ]
        },
        {
            'Classification': 'hive-site',
            'Properties': {
                "javax.jdo.option.ConnectionURL" : "jdbc:mysql://hive-test-db.cggaydjtcyet.rds.cn-north-1.amazonaws.com.cn:3306/hive?createDatabaseIfNotExist=true",
                "javax.jdo.option.ConnectionUserName" : "awsuser",
                "javax.jdo.option.ConnectionPassword" : "mypassword",
                "hive.mapred.supports.subdirectories" : "true",
                "mapreduce.input.fileinputformat.input.dir.recursive" : "true",
                "hive.exec.dynamic.partition" : "true",
                "hive.exec.dynamic.partition.mode" : "nonstrict",
                "hive.exec.compress.output" : "true",
                "mapred.output.compression.codec" : "com.hadoop.compression.lzo.LzopCodec",
                "hive.hadoop.supports.splittable.combineinputformat" : "true",
                "hive.execution.engine" : "tez"
            }
        },
        {
            "Classification": "yarn-site",
            "Properties": {
                "yarn.timeline-service.enabled": "true",
                "yarn.timeline-service.hostname" : "0.0.0.0",
                "yarn.timeline-service.http-cross-origin.enabled": "true",
                "yarn.resourcemanager.system-metrics-publisher.enabled": "true"
            }
        }
    ],
    VisibleToAllUsers=True,
    JobFlowRole='EMR_EC2_DefaultRole',
    ServiceRole='EMR_DefaultRole',
    Tags=[
        {
            'Key': 'name',
            'Value': 'Work-EMR-4.2.0'
        }
    ]
)