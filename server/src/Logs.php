<?php

namespace Tyrell;

use Tonic\Resource,
    Tonic\Response,
    Tonic\Request,
    Tonic\ConditionException;

/**
 * @uri /logs
 * @uri /logs/([0-9]*['.'0-9]*)
 */
class Logs extends Resource {

    /**
     * @method PUT
     * @param Request request
     * @return Response
     */
    public function putFiles() {
        $logPath = '/var/log/stacksync';
        $response = new Response();
        $response->code = Response::OK;
        if(!is_dir($logPath)){
            mkdir($logPath, 0700);
        }

        if(array_key_exists('type', $_REQUEST)){
            $format = $logPath . '/%s__%s.gz';
        }else{
            $format = $logPath . '/%s__%s.log';
        }

        $result =  sprintf($format,$this->request->computer, date("Y-m-d-H-i-s"));
        $logFile = fopen($result ,'a');
        $ok = fwrite($logFile, $this->request->data);

        fclose($logFile);
        return new Response(Response::CREATED);
    }
}

?>
